package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.models.Code;
import platform.repository.CodeRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeService {

    @Autowired
    private CodeRepo codeRepo;

    public Code save(Code code) {
        System.out.println("saving new code: "+code);
        return codeRepo.save(code);
    }

    public Code findById(UUID id) {
        Optional<Code> c = codeRepo.findById(id);
        if(c.isEmpty()) {
            return null;
        } else {
            return c.get();
        }
    }

    public List<Code> findLatest() {
        return codeRepo.findLatest();
    }

    public void deleteById(UUID id) {
        codeRepo.deleteById(id);
    }

    public Code clientAccessCodeById(UUID id) {
        Code code = findById(id);
        int timeAlive = 0;
        if(code != null) {
            if(code.getTime() != 0) {
                timeAlive = code.timeAlive();
            }
            if(timeAlive < 0 ) {
                codeRepo.deleteById(id);
                return null;
            }
            if(code.getViews() != 0 ) {
                if(code.getViews() - 1 == 0) {
                    System.out.println("Last view -> delete");
                    codeRepo.deleteById(id);
                    code.setViews(0);
                } else {
                    System.out.println("views decrease 1. code id: "+id);
                    int views = code.getViews();
                    code.setViews(views - 1);
                    save(code);
                }

            }
            code.setTime(timeAlive);
        }
        return code;
    }

    public Code clientAddNewCode(Code code) {
        if(code.getTime() <= 0) {
            code.setRestrictTime(false);
            code.setTime(0);
        }
        if(code.getViews() <= 0) {
            code.setRestrictViews(false);
            code.setViews(0);
        }

        code.setDate(LocalDateTime.now());
        return save(code);
    }
}
