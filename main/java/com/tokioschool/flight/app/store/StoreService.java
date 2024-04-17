package com.tokioschool.flight.app.store;

import com.tokioschool.flight.app.store.dto.ResourceContentDTO;
import com.tokioschool.flight.app.store.dto.ResourceIdDTO;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.util.UUID;

public interface StoreService {

    Optional<ResourceIdDTO>saveResource(MultipartFile multipartFile, @Nullable String description);

     Optional<ResourceContentDTO> findResource(UUID resourceId);

     void deleteResource(UUID resourceId);
}
