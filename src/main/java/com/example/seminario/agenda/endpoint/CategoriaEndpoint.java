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
import com.example.seminario.agenda.model.CategoriaDTO;
import com.example.seminario.agenda.model.CategoriaVO;
import com.example.seminario.agenda.service.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/categorias")
public class CategoriaEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(CategoriaEndpoint.class);

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/insert")
	public ResponseEntity<ResponseBean<Void>> insert(@RequestBody CategoriaDTO categoriaDTO) {
		ResponseEntity<ResponseBean<Void>> res = null;
		LOG.info("insert()->CourseDTO: {}", categoriaDTO);
		try {
            categoriaService.insert(categoriaDTO.getDescripcion());
			res = Utils.response200OK("Datos guardados correctamente");
		} catch (Exception e) {
			res = Utils.handle(e, "Error al guardar los datos");
		}
		LOG.info("insert()->res: {}", res);
		return res;
	}

    @GetMapping("/delete")
	public ResponseEntity<ResponseBean<Void>> delete(@RequestParam int id) {
		ResponseEntity<ResponseBean<Void>> res = null;
		try {
            categoriaService.delete(id);
			res = Utils.response200OK("Datos eliminados correctamente");
		} catch (Exception e) {
			res = Utils.handle(e, "Datos no eliminados");
		}
		LOG.info("delete()->res: {}", res);
		return res;
	}

    @GetMapping("/findAll")
	public ResponseEntity<ResponseBean<List<CategoriaVO>>> findAll() {
		ResponseEntity<ResponseBean<List<CategoriaVO>>> res = null;
        List<CategoriaVO> contactoVOs = null;
		try {
            contactoVOs = categoriaService.findAll();
			res = Utils.response200OK("Datos recuperados correctamente",contactoVOs);
		} catch (Exception e) {
			res = Utils.handle(e, "Error al recuperar los datos");
		}
		LOG.info("findAll()->res: {}", res);
		return res;
	}

}
