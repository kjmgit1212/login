package com.goodee.ex16.service;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.goodee.ex16.domain.FileAttachDTO;
import com.goodee.ex16.domain.GalleryDTO;
import com.goodee.ex16.mapper.GalleryMapper;
import com.goodee.ex16.util.MyFileUtils;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class GalleryServiceImpl implements GalleryService {
	@Autowired
	private GalleryMapper galleryMapper;
	
	// 갤러리 삽입
		@Transactional
		@Override
		public void save(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
			
			// 전달된 파라미터
			String writer = multipartRequest.getParameter("writer");
			String title = multipartRequest.getParameter("title");
			String content = multipartRequest.getParameter("content");
			
			// IP
			Optional<String> opt = Optional.ofNullable(multipartRequest.getHeader("X-Forwarded-For"));
			String ip = opt.orElse(multipartRequest.getRemoteAddr());
			
			// GalleryDTO
			GalleryDTO gallery = GalleryDTO.builder()
					.writer(writer)
					.title(title)
					.content(content)
					.ip(ip)
					.build();
			
			// Gallery INSERT 수행
			// System.out.println(gallery);  // INSERT 수행 전에는 gallery에 galleryNo값이 없다.
			int galleryResult = galleryMapper.insertGallery(gallery);  // INSERT 수행
			// System.out.println(gallery);  // INSERT 수행 후에는 selectKey에 의해서 gallery에 galleryNo값이 저장된다.

			// 결론. FILE_ATTACH 테이블에 INSERT할 galleryNo 정보는 gallery에 저장되어 있다.
			
			// 첨부된 모든 파일들
			List<MultipartFile> files = multipartRequest.getFiles("files");  // 파라미터 files
			
			// 파일 첨부 결과
			int fileAttachResult;
			if(files.get(0).getOriginalFilename().isEmpty()) {  // 첨부가 없으면 files.size() == 1임. [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]] 값을 가짐.
				fileAttachResult = 1; 
			} else {  // 첨부가 있으면 "files.size() == 첨부파일갯수"이므로 fileAttachResult = 0으로 시작함.
				fileAttachResult = 0;
			}
			
			for (MultipartFile multipartFile : files) {
				
				// 예외 처리는 기본으로 필요함.
				try {
					
					// 첨부가 없을 수 있으므로 점검해야 함.
					if(multipartFile != null && multipartFile.isEmpty() == false) {  // 첨부가 있다.(둘 다 필요함)
						
						// 첨부파일의 본래 이름(origin)
						String origin = multipartFile.getOriginalFilename();
						origin = origin.substring(origin.lastIndexOf("\\") + 1);  // IE는 본래 이름에 전체 경로가 붙어서 파일명만 빼야 함.
						
						// 첨부파일의 저장된 이름(saved)
						String saved = MyFileUtils.getUuidName(origin);
						
						// 첨부파일의 저장 경로(디렉터리)
						String path = MyFileUtils.getTodayPath();
						
						// 저장 경로(디렉터리) 없으면 만들기
						File dir = new File(path);
						if(dir.exists() == false) {
							dir.mkdirs();
						}
						
						// 첨부파일
						File file = new File(dir, saved);
						
						// 첨부파일 확인
						String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type(image/jpeg, image/png, image/gif)
						if(contentType.startsWith("image")) {
							
							// 첨부파일 서버에 저장(업로드)
							multipartFile.transferTo(file);
							
							// 썸네일 서버에 저장(썸네일 정보는 DB에 저장되지 않음)
							Thumbnails.of(file)
								.size(100, 100)
								.toFile(new File(dir, "s_" + saved));
							
							// FileAttachDTO
							FileAttachDTO fileAttach = FileAttachDTO.builder()
									.path(path)
									.origin(origin)
									.saved(saved)
									.galleryNo(gallery.getGalleryNo())
									.build();
							
							// FileAttach INSERT 수행
							fileAttachResult += galleryMapper.insertFileAttach(fileAttach);
							
						}

					}
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
			// 응답
			try {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				if(galleryResult == 1 && fileAttachResult == files.size()) {
					out.println("<script>");
					out.println("alert('갤러리가 등록되었습니다.')");
					out.println("location.href='" + multipartRequest.getContextPath() + "/gallery/list'");
					out.println("</script>");
					out.close();
				} else {
					out.println("<script>");
					out.println("alert('갤러리가 등록되지 않았습니다.')");
					out.println("history.back()");
					out.println("</script>");
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
