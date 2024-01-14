package es.iesjandula.videoclub.exception;

import java.util.HashMap;
import java.util.Map;

public class MovieError extends Exception 
{

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -4027694450822277243L;
	
	private Integer movieErrorId;
	
	private String description;
	
	/**
	 * Constructor que crea una excepcion 
	 * @param movieErrorId
	 * @param description
	 */
	public MovieError(Integer movieErrorId,String description)
	{
		super();
		this.movieErrorId = movieErrorId;
		this.description = description;
	}
	/**
	 * Metodo que devuelve un mapa con la descripcion del error
	 * @return mapa con la descripcion del error
	 */
	public Map<String,Object> getBodyMessageException()
	{
		Map<String,Object> error = new HashMap<String,Object>();
		
		error.put("code", movieErrorId);
		error.put("message", description);
		return error;
	}

}
