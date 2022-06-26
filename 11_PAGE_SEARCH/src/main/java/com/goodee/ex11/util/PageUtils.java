package com.goodee.ex11.util;

public class PageUtils {
	private int totalRecord;		// 전체 레코드	/ DB에서 구하기
	private int recordPerPage = 5;		// 페이지당 레코드	/ 정하기 나름
	private int totalPage;			// 전체 페이지수
	
	
	private int page;			// 파라미터
	private int beginRecord;	// 페이지 시작 레코드
	private int endRecord;		// 페이지 끝 레코드
	
	
	private int pagePerBlock = 5;
	private int beginPage;
	private int endPage;
	
	
	
	
	
	public void setPageEntity(int totalRecord, int page) {
		
		// 필드값 저장
		this.totalRecord = totalRecord;
		this.page = page;
		
		// totalPage 계산
		
		totalPage = totalRecord / recordPerPage;
		if(totalRecord % recordPerPage != 0) {
			totalPage++;
		}
		
		
		// beginRecord, endRecord
		beginRecord = (page - 1) * recordPerPage + 1; 
		endRecord = beginRecord + recordPerPage -1;
		if(endRecord > totalRecord) {
			endRecord = totalRecord;
		}

		
		// beginPage, endPage
		
		beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1; 
		endPage = beginPage + pagePerBlock -1;
		if(endPage > totalPage) {
			endPage = totalPage;
		}
	
	}
	
	public String getPaging(String path) {
			
		StringBuilder sb = new StringBuilder();
		
		// 전달되는 path의 경우
		// 1. 파라미터가 없는경우	employee/list
		// 							employee/list? page= 1
		// 2. 파라미터가 있는경우	employee/search?
		//							employee/search?column=값&query=값  !&!  page= 1
		
		String concat = path.contains("?") ? "&" : "?";
		path  += concat;
		
		
		// 첫번째 페이지로 이동
		if( page == 1) {
			sb.append("start");
		}else {
			sb.append("<a href=\" " + path +"page=" + 1 + "\"> start </a>");
		}
		
		// 이전 블록으로 이동하기
		if( page <= pagePerBlock ) {
			sb.append("prevBlock");
		}else {
			sb.append("<a href=\" " + path +"page=" + (beginPage -1 ) + "\"> prevBlock </a>");
		}
		
		// prev
		if( page == 1 ) {
			sb.append("prev");
		}else {
			sb.append("<a href=\" " + path +"page=" + (page -1 ) + "\"> prev </a>");
		}
		
		// 페이지 번호, 현재 페이지는 a태그가 없다
		for(int p = beginPage; p <= endPage; p++) {
			if(p == page) {
				sb.append(p);
			}else {
				sb.append("<a href=\" " + path +"page=" + p + "\">" + p + "</a>");
			}
		}
		
		// next	
		if( page ==  totalPage ) {
			sb.append("next");
		}else {
			sb.append("<a href=\" " + path +"page=" + (page + 1 ) + "\"> next </a>");
		}
		
		// 다음블록으로 이동하기
		if(endPage == totalPage) {
			sb.append("nextBlock");
		}else {
			sb.append("<a href=\" " + path +"page=" + (endPage + 1 ) + "\"> nextBlock </a>");
		}
		
		
		// 마지막 페이지로 이동
		if( page == totalPage ) {
			sb.append("end");
		}else {
			sb.append("<a href=\" " + path +"page=" + totalPage + "\"> end </a>");
		}
		
		return sb.toString();
	}



	// getter / setter
	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPage() {
		return page;
	}





	public void setPage(int page) {
		this.page = page;
	}





	public int getBeginRecord() {
		return beginRecord;
	}





	public void setBeginRecord(int beginRecord) {
		this.beginRecord = beginRecord;
	}





	public int getEndRecord() {
		return endRecord;
	}





	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}





	public int getPagePerBlock() {
		return pagePerBlock;
	}





	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}





	public int getBeginPage() {
		return beginPage;
	}





	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}





	public int getEndPage() {
		return endPage;
	}





	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}


	

	



	
	
	
	
	
	
	
	
}
