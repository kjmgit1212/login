package com.goodee.ex05.service;


import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

import com.goodee.ex05.domain.ReservationDTO;

public class ReservationServiceImpl implements ReservationService {

	@Override
	public ResponseEntity<ReservationDTO> detail1(HttpServletRequest request) {
		
		Optional<String> optNo = Optional.ofNullable(request.getParameter("no"));
		Long no = Long.parseLong(optNo.orElse("0"));
		
		// no의 값이 0인 경우 조회가 불가능한 경우
		
		// ResponseEntity<T>
		// 1. 실제 응답 데이터는 T타입이다.
		// 2. HttpHeaders 클래스를 이용해서 응답데이터의 Content-Type을 지정한다.
		//	  produces를 사용하지 않는다.
		// 3. 응답 코드(HttpStatus)를 저장할 수 있다.
		
		ResponseEntity<ReservationDTO> responseEntity = null;
		
		// ResponseEntity로 전달할 결과 데이터
		ReservationDTO reservation = new  ReservationDTO(no, "예약자");
		
		// ResponseEntity로 전달할 응답 데이터의 Content-Type
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json; chaset=UTF-8");
		
		if(no > 0) {
			responseEntity = new ResponseEntity<ReservationDTO>(reservation, header, HttpStatus.OK);	// ajax의 success로
		}else {
			responseEntity = new ResponseEntity<ReservationDTO>(null, header, HttpStatus.BAD_REQUEST);	// ajax의 error로
		}
		
		
		return responseEntity;
	}

	@Override
	public ResponseEntity<ReservationDTO> detail2(Long no) {
		ResponseEntity<ReservationDTO> responseEntity = null;
		
		ReservationDTO reservation = new ReservationDTO(no, "예약자");
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json; charset=UTF-8");
		
		if(no > 0) {
			responseEntity = new ResponseEntity<ReservationDTO>(reservation, header, HttpStatus.OK);
		}else {
			responseEntity = new ResponseEntity<ReservationDTO>(null, header, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return responseEntity;
	}

	@Override
	public ResponseEntity<ReservationDTO> detail3(ReservationDTO reservation) {
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		// no가 100을 초과하면 저장할 수 없는 데이터로 가정
		ResponseEntity<ReservationDTO> result = null;
		
		if(reservation.getNo() > 100) {
			result = new ResponseEntity<ReservationDTO>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			result = new ResponseEntity<ReservationDTO>(new ReservationDTO(reservation.getNo(), "예약자"), header, HttpStatus.OK);
		}
		
		return result;
	}

	@Override
	public ResponseEntity<byte[]> image() {
		File file = new File("D:", "eagle.jpg");
		ResponseEntity<byte[]> result = null;
		try {
			// c:eagle.jsp 파일 복사해서 바이트배열에 저장 후 해당 배열을 반환
			byte[] b =  FileCopyUtils.copyToByteArray(file);
			
			// HttpHeaders 반환할 데이터의 Content-Type
			// jpg 이미지의 Content-Type은 image/jpg
			
			HttpHeaders header = new HttpHeaders();
			
			String contentType = Files.probeContentType(file.toPath());
			
			header.add("Content-Type", contentType);
		
			// 반환할 ResponseEntity
			result = new ResponseEntity<byte[]>(b, header, HttpStatus.OK);
			
		}catch (Exception e) {
			e.printStackTrace();
	}
		return result;
}
}
