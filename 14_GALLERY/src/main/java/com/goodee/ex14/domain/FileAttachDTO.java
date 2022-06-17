package com.goodee.ex14.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileAttachDTO {
		private Long fileAttachNo;
		private String path;
		private String origin;
		private String saved;
		private Integer downloadCnt;
		 private Long galleryNo;
		// private GalleryDTO gallery;
}
