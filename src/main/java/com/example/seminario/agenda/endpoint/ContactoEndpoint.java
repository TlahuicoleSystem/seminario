package com.example.seminario.agenda.endpoint;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.seminario.agenda.Utils.ResponseBean;
import com.example.seminario.agenda.Utils.Utils;
import com.example.seminario.agenda.model.ContactoDTO;
import com.example.seminario.agenda.model.ContactoVO;
import com.example.seminario.agenda.service.ContactoService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/contacto")
public class ContactoEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ContactoEndpoint.class);

    @Autowired
    private ContactoService contactoService;

    @PostMapping("/insert")
	public ResponseEntity<ResponseBean<Void>> insert(@RequestBody ContactoDTO contactoDTO) {
		ResponseEntity<ResponseBean<Void>> res = null;
		LOG.info("insert()->CourseDTO: {}", contactoDTO);
		try {
				contactoService.insert(contactoDTO);
				res = Utils.response200OK("Datos guardados correctamente");
		} catch (Exception e) {
			res = Utils.handle(e, "Error al guardar los datos");
		}
		LOG.info("insert()->res: {}", res);
		return res;
	}

    @PostMapping("/update")
	public ResponseEntity<ResponseBean<Void>> update(@RequestBody ContactoDTO contactoDTO, @RequestParam int id) {
		ResponseEntity<ResponseBean<Void>> res = null;
		LOG.info("update()->contactoDTO: {}", contactoDTO);
		try {
                contactoService.upddate(id, contactoDTO);
				res = Utils.response200OK("Datos actualizados correctamente");
		} catch (Exception e) {
			res = Utils.handle(e, "Error al actualizar los datos");
		}
		LOG.info("insert()->res: {}", res);
		return res;
	}

    @GetMapping("/delete")
	public ResponseEntity<ResponseBean<Void>> delete(@RequestParam int id) {
		ResponseEntity<ResponseBean<Void>> res = null;
		try {
            contactoService.delete(id);
			res = Utils.response200OK("Datos eliminados correctamente");
		} catch (Exception e) {
			res = Utils.handle(e, "Datos no eliminados");
		}
		LOG.info("delete()->res: {}", res);
		return res;
	}

    @GetMapping("/findByCategoria")
	public ResponseEntity<ResponseBean<List<ContactoVO>>> findByCategoria(@RequestParam int idtCategoria) {
		ResponseEntity<ResponseBean<List<ContactoVO>>> res = null;
        List<ContactoVO> contactoVOs = null;
		try {
            int id = idtCategoria;
            contactoVOs = contactoService.findContactoByCategoria(id);
			res = Utils.response200OK("Datos recuperados correctamente",contactoVOs);
		} catch (Exception e) {
			res = Utils.handle(e, "Datos recuperados correctamente");
		}
		LOG.info("findByCategoria()->res: {}", res);
		return res;
	}

    @GetMapping("/findAll")
	public ResponseEntity<ResponseBean<List<ContactoVO>>> findAll() {
		ResponseEntity<ResponseBean<List<ContactoVO>>> res = null;
        List<ContactoVO> contactoVOs = null;
		try {
            contactoVOs = contactoService.findAll();
			res = Utils.response200OK("Datos recuperados correctamente",contactoVOs);
		} catch (Exception e) {
			res = Utils.handle(e, "Error al recuperar los datos");
		}
		LOG.info("findAll()->res: {}", res);
		return res;
	}

}
