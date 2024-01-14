package es.iesjandula.videoclub.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie 
{
	private Long movieId;
	
	private String title;
	
	private String duration;
}
