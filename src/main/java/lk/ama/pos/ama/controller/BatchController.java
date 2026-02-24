package lk.ama.pos.ama.controller;

import lk.ama.pos.ama.dto.BatchDTO;
import lk.ama.pos.ama.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batches")
@CrossOrigin(origins = "http://localhost:5173")
public class BatchController {
    @Autowired
    private BatchService service;

    @PostMapping
    public boolean saveBatch(@RequestBody BatchDTO dto) {
        return service.saveBatch(dto);
    }

    @PutMapping
    public boolean updateBatch(@RequestBody BatchDTO dto) {
        return service.updateBatch(dto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBatch(@PathVariable int id) {
        return service.deleteBatch(id);
    }
}
