package urjc.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import urjc.exception.BadRequest;
import urjc.models.Bicicleta;
import urjc.models.BicicletaResponse;
import urjc.service.BicicletaService;

@RestController
@RequestMapping("/bike")



public class BicicletaController {
	
	
	@Autowired
	private BicicletaService bicicletaService;
	
    @Operation(summary = "Reserva bicicleta")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Reservar una bicicleta", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = BicicletaResponse.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid user id , station id or bike id", content = @Content),
    @ApiResponse(responseCode = "404", description = "User, station or bike not found", content = @Content) })
	@PutMapping("/reservaBicicleta/{idBici}")
    public ResponseEntity<BicicletaResponse> reservaBicicleta(@RequestParam  long idUser,@RequestParam long idEst,@PathVariable long idBici){
        
    	return bicicletaService.reservarBicicleta(idBici,idUser,idEst);
    	
    }
	
    
    @Operation(summary = "Devolver bicicleta")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Devolver una bicicleta", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = BicicletaResponse.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid user id , station id or bike id", content = @Content),
    @ApiResponse(responseCode = "404", description = "User, station or bike not found", content = @Content) })
	@PutMapping("/devolverBicicleta/{idBici}")
    public ResponseEntity<BicicletaResponse> devolverBicicleta(
													    		@RequestParam long idUser,
													    		@RequestParam long idEst,
													    		@PathVariable long idBici){
		
		return bicicletaService.devolverBicicleta(idUser, idEst, idBici);
		
	}

	
	
}
