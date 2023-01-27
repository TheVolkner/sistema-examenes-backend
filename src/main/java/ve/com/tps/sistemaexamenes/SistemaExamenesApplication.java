package ve.com.tps.sistemaexamenes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ve.com.tps.sistemaexamenes.entity.Rol;
import ve.com.tps.sistemaexamenes.entity.UsuarioRol;
import ve.com.tps.sistemaexamenes.entity.Usuarios;
import ve.com.tps.sistemaexamenes.services.UsuarioService;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
//COMMAND LINE RUNNER PERMITE EJECUTAR UN COMANDO AL EJECUTAR LA APLICACION
public class SistemaExamenesApplication{

	//INJECTAMOS AL SERVICIO PARA PROBAR LA FUNCIONALIDAD
	/* @Autowired
	private UsuarioService usuarioService; */

	public static void main(String[] args) {
		SpringApplication.run(SistemaExamenesApplication.class, args);
	}

	/*
	//LA INTERFAZ MANDA A IMPLMEMENTAR ESTE METODO QUE INDICA A LA APLICACION EL CODIGO A EJECUTAR AL INICIO
	@Override
	//CREAMOS UN USUARIO Y LE INDICAMOS UN ROL
	public void run(String... args) throws Exception {
		Usuarios u = new Usuarios();

		u.setNombre("Omar");
		u.setApellido("Arvelo");
		u.setEmail("omar@ejemplo.com");
		u.setUsername("OmarBlz");
		u.setPassword("omar123");
		u.setTelefono("555-578-52");
		u.setPerfil("foto.png");

		Rol r = new Rol();

		r.setRol_id(1);
		r.setNombre("ADMIN");

		//INDICAMOS EL SET DE USUARIOS CON ROLES
		Set<UsuarioRol> usuarioRolSet = new HashSet<>();
		UsuarioRol usuarioRol = new UsuarioRol();

		usuarioRol.setUsuario(u);
		usuarioRol.setRol(r);

		//LISTAMOS EL USUARIO CON ROL AL SET, MISMO PROCEDIMIENTO PARA AGREGAR MAS
		usuarioRolSet.add(usuarioRol);

		//GUARDAMOS
		Usuarios uGuardado = usuarioService.guardarUsuario(u,usuarioRolSet);

		System.out.println(uGuardado.getNombre());

	} */
}
