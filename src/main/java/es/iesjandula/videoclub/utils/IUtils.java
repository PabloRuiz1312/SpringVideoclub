package es.iesjandula.videoclub.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.iesjandula.videoclub.exception.MovieError;
import es.iesjandula.videoclub.models.Movie;
import es.iesjandula.videoclub.models.User;

public interface IUtils extends IChecker
{
	static Logger log = LogManager.getLogger();
	/**
	 * Metodo que parsea el json a una lista de movies
	 * @return
	 * @throws MovieError
	 */
	public default List<Movie> parseMovieJson() throws MovieError
	{
		List<Movie> movies = null;
		ObjectMapper mapper = Json.mapper();
		try
		{
			movies = mapper.readValue(Paths.get("src/main/resources/json.json").toFile(),mapper.getTypeFactory()
					.constructCollectionType(List.class, Movie.class));
		}
		catch(IOException ex)
		{
			log.error("Error en el parseo",ex);
			throw new MovieError(5,"El json no contiene datos sobre peliculas");
		}
		return movies;
	}
	/**
	 * Metodo que escribe las peliculas a un json
	 * @param movies
	 * @throws MovieError
	 */
	public default void writeMovies(List<Movie> movies) throws MovieError
	{
		ObjectMapper mapper = Json.mapper();
		File file = new File("src/main/resources/movies.json");
		try
		{
			if(movies==null)
			{
				throw new MovieError(6,"No se ha cargado ninguna pelicula en sesion");
			}
			else if(movies.isEmpty())
			{
				throw new MovieError(6,"No se ha cargado ninguna pelicula en sesion");
			}
			this.checkDeepMovie(movies);
			mapper.writeValue(file, movies);
		}
		catch(IOException ex )
		{
			log.error("Error al escribir las peliculas a un json",ex);
			throw new MovieError(8,"Error al escribir las peliculas a un json");
		}
	}
	/**
	 * Metodo que parsea el json a una lista de users
	 * @return
	 * @throws MovieError
	 */
	public default List<User> parseUserJson() throws MovieError
	{
		List<User> users = null;
		ObjectMapper mapper = Json.mapper();
		try
		{
			users = mapper.readValue(Paths.get("src/main/resources/json.json").toFile(),mapper.getTypeFactory()
					.constructCollectionType(List.class, User.class));
		}
		catch(IOException ex)
		{
			log.error("Error en el parseo",ex);
			throw new MovieError(5,"El json no contiene datos sobre peliculas");
		}
		return users;
	}
}
