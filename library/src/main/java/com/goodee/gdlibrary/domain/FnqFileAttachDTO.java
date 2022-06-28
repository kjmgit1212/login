package com.goodee.gdlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FnqFileAttachDTO {
	   private Long fnqFileAttachNo;
	   private String fnqFileAttachPath;
	   private String fnqFileAttachOrigin;
	   private String fnqFileAttachSaved;
	   private Long fnqNo;

}
