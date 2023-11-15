package baeksproject.project.config;

import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.memory.MemoryItemRepository;
import baeksproject.project.item.service.ItemService;
import baeksproject.project.item.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    //@Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    //@Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }
}
