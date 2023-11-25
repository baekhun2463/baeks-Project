package baeksproject.project.item.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.ItemSearchCond;
import baeksproject.project.item.repository.ItemUpdateDto;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceV1 implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberRespository memberRespository;


    public Item saveItemWithMember(Long memberId, Item item, MultipartFile imageFile) {
        Member member = memberRespository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        item.setMember(member);

        String imagePath = saveImageFile(imageFile);
        log.info("imagePath={}", imagePath);
        item.setImagePath(imagePath);
        return itemRepository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        itemRepository.update(itemId, updateParam);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findItems(ItemSearchCond cond) {
        return itemRepository.findAll(cond);
    }

    private String saveImageFile(MultipartFile imageFile) {
            if (imageFile.isEmpty()) {
                // 파일이 없는 경우, 적절한 처리를 합니다.
                return null;
            }

            try {
                // 저장할 디렉토리 경로
                String directoryPath = "src/main/resources/static/images";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs(); // 디렉토리가 없으면 생성
                }

                // 파일 이름 생성 (예: 원본 파일 이름, 혹은 UUID를 사용하여 고유한 이름 생성)
                String originalFilename = imageFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

                // 파일 저장 경로
                Path filePath = Paths.get(directoryPath+ "/" + storedFileName);
                Files.copy(imageFile.getInputStream(), filePath);

                return "/images/" + storedFileName;


            } catch (IOException e) {
                // 파일 저장 중 오류 발생 시 처리
                e.printStackTrace();
                return null;
            }
    }

}