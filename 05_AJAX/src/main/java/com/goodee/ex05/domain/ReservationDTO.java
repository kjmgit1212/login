package com.goodee.ex05.domain;

public class ReservationDTO {
		private Long no;
		private String name;
		public ReservationDTO() {
			// TODO Auto-generated constructor stub
		}
		public ReservationDTO(Long no, String name) {
			super();
			this.no = no;
			this.name = name;
		}
		
		// getter / setter
		public Long getNo() {
			return no;
		}
		public void setNo(Long no) {
			this.no = no;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		
}
