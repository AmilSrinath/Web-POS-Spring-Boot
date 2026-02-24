package lk.ama.pos.ama.service;

import lk.ama.pos.ama.dto.BatchDTO;
import lk.ama.pos.ama.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {
    @Autowired
    private BatchRepository repository;

    public boolean saveBatch(BatchDTO dto) {
        return repository.saveBatch(dto) > 0;
    }

    public boolean updateBatch(BatchDTO dto) {
        return repository.updateBatch(dto) > 0;
    }

    public boolean deleteBatch(int id) {
        return repository.deleteBatch(id) > 0;
    }
}
