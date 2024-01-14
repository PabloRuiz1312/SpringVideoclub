package es.iesjandula.videoclub.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User 
{
	private Long userId;
	
	private String userName;
}
