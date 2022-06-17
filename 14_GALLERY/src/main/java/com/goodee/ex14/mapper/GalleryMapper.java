package com.goodee.ex14.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.ex14.domain.FileAttachDTO;
import com.goodee.ex14.domain.GalleryDTO;

@Mapper
public interface GalleryMapper {

	public int insertGallery(GalleryDTO gallery);
	public int insertFileAttach(FileAttachDTO fileAttach);
	
	public int selectGalleryCount();
	public List<FileAttachDTO> selectGalleryList(Map<String, Object> map);
	
	public FileAttachDTO selectFileAttachByNo(Long fileAttachNo);
	public int updateDownloadCnt(Long fileAttachNo);
	
	public GalleryDTO selectGalleryByNo(Long galleryNo);
	public List<FileAttachDTO> selectFileAttachListInTheGallery(Long galleryNo);
	public int updateGalleryHit(Long galleryNo);
	
	public int deleteGallery(Long galleryNo);
	
	// 갤러리 수정
	public int updateGallery(GalleryDTO gallery);
	
	// 첨부파일 삭제
	public int deleteFileAttach(Long fileAttachNo);
	
	// 어제저장된 목록
	public List<FileAttachDTO> selectFileAttachListAtYesterday();
	
	
}
