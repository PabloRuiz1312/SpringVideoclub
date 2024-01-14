package es.iesjandula.videoclub.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.videoclub.exception.MovieError;
import es.iesjandula.videoclub.models.Movie;

public interface IChecker 
{
	static Logger log = LogManager.getLogger();
	
	public default void checkFile(MultipartFile file) throws MovieError
	{
		if(file==null)
		{
			throw new MovieError(1,"No se ha pasado ningun fichero");
		}
		else if(!file.getOriginalFilename().endsWith(".json") && !file.getOriginalFilename().endsWith(".JSON"))
		{
			throw new MovieError(2,"El fichero no es un json");
		}
		else if(file.isEmpty())
		{
			
			throw new MovieError(3,"El fichero esta vacio");
		}
		else
		{
			File jsonFile = new File("src/main/resources/json.json");
			FileWriter fw = null;
			
			try
			{
				fw = new FileWriter(jsonFile);
				String content = new String(file.getBytes());
				fw.write(content);
				fw.flush();
			}
			catch(IOException ex)
			{
				log.error("Error al escribir el fichero json",ex);
				throw new MovieError (4,"Error al trasladar el fichero al sistema");
			}
			finally
			{
				try
				{
					if(fw!=null)
					{
						fw.close();
					}
				}
				catch(IOException ex)
				{
					log.error("Error al cerrar el fichero",ex);
				}
			}
		}
	}
	/**
	 * Metodo que comprueba que las peliculas de la lista de movies no tengan datos vacios
	 * @param movies
	 * @throws MovieError
	 */
	public default void checkDeepMovie(List<Movie> movies) throws MovieError
	{
		for(Movie m:movies)
		{
			if(m.getDuration().isEmpty() || m.getMovieId()==null || m.getTitle().isEmpty())
			{
				throw new MovieError(7,"El json contiene peliculas con datos vacios");
			}
		}
	}
}
