package es.iesjandula.videoclub.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoReview 
{
	private String userName;
	
	private String review;
}
