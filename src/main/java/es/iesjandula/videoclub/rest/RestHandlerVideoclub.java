package es.iesjandula.videoclub.rest;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.videoclub.exception.MovieError;
import es.iesjandula.videoclub.models.Movie;
import es.iesjandula.videoclub.models.User;
import es.iesjandula.videoclub.utils.IChecker;
import es.iesjandula.videoclub.utils.IUtils;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping( value = "/videoclub", produces = "application/json" )
public class RestHandlerVideoclub implements IChecker,IUtils
{
	private static Logger log = LogManager.getLogger();

	private List<Movie> movies;
	private List<User> users;
	/**
	 * Default constructor
	 */
	public RestHandlerVideoclub()
	{
		//Public constructor
		this.movies = new LinkedList<Movie>();
		this.users = new LinkedList<User>();
	}
	
	@RequestMapping( method = RequestMethod.POST,value = "/movies",consumes = "multipart/form-data")
	public ResponseEntity <?> uploadMovies(
			@RequestPart( value = "jsonFile") MultipartFile jsonFile, 
			HttpSession sesion
			)
	{
		try
		{
			this.checkFile(jsonFile);
			this.movies = this.parseMovieJson();
			sesion.setAttribute("movies", this.movies);
			return ResponseEntity.ok().body(this.movies);
		}
		catch(MovieError ex)
		{
			log.error("Error al recibir el fichero",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error de servidor",ex);
			return ResponseEntity.status(500).body("Error de servidor"+ex);
		}
	}
	
	@RequestMapping ( method = RequestMethod.GET,value = "/booking",produces = "multipart/form-data")
	public ResponseEntity <?> downloadMovies(HttpSession sesion)
	{
		try
		{
			this.movies = (List<Movie>) sesion.getAttribute("movies");
			this.writeMovies(movies);
			File file = new File("src/main/resources/movies.json");
			//DEVOLVER EL FICHERO
			byte [] array = Files.readAllBytes(file.toPath());
			HttpHeaders response = new HttpHeaders();
			response.set("Content-Disposition", "attachment; filename=" + file.getName());
			return ResponseEntity.ok().headers(response).body(array);
		}
		catch(MovieError ex)
		{
			log.error("Error al recibir el fichero",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error de servidor",ex);
			return ResponseEntity.status(500).body("Error de servidor"+ex);
		}
	}
	
	@RequestMapping ( method = RequestMethod.GET,value = "/movies")
	public ResponseEntity <?> getMovies()
	{
		try
		{
			return ResponseEntity.ok().body(this.movies);
		}
		catch(Exception ex)
		{
			log.error("Error de servidor",ex);
			return ResponseEntity.status(500).body("Error de servidor"+ex);
		}
	}
	
	@RequestMapping ( method = RequestMethod.GET,value = "/movies-http")
	public ResponseEntity <?> getMoviesHttp()
	{
		try
		{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			String url = "http://localhost:8080/videoclub/movies";
			//PARAMETROS PATH
			//url.replace("{param}","paramReplaced");
			
			//PARAMETROS QUERY
			//URIBuilder uri = new URIBuilder("url");
			//uri.setParameter("nombre","valor");
			//String urlQuery = uri.build().toString();
			
			//PARAMETROS HEADER
			//HttpGEt request = new HttpGet("url);
			//request.addHeader("clave","valor");
			
			//PARAMETROS BODY
			//ObjectMapper objectMapper = new ObjectMapper();
			//String jsonBody = objectMapper.writeValueAsString(new Objeto(atts));
			//StringEntity requestBody = new StringEntity(jsonBody);
			//HttpGet request = new HttpGet("url");
			//request.setEntity(requestBody);
			
			//PARAMETROS BODY CON ARRAY
			//List<Objeto> objetos = Arrays.asList(objetos);
			//Mismos pasos de antes
			HttpGet request = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(request);
			String responseBody = EntityUtils.toString(response.getEntity());
			return ResponseEntity.ok().body(responseBody);
		}
		catch(Exception ex)
		{
			log.error("Error de servidor",ex);
			return ResponseEntity.status(500).body("Error de servidor"+ex);
		}
	}
	@RequestMapping( method = RequestMethod.POST, value = "/users",consumes = "multipart/form-data")
	public ResponseEntity <?> uploadUser(
			@RequestPart( value = "jsonFile") MultipartFile jsonFile, 
			HttpSession sesion
			)
	{
		try
		{
			this.checkFile(jsonFile);
		
			sesion.setAttribute("movies", this.movies);
			return ResponseEntity.ok().body(this.movies);
		}
		catch(MovieError ex)
		{
			log.error("Error al recibir el fichero",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error de servidor",ex);
			return ResponseEntity.status(500).body("Error de servidor"+ex);
		}
	}
	
	
}
