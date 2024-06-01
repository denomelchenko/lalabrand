package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.ItemService;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.item.size.SizeService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ItemInfoService {
    private final ItemInfoRepository itemInfoRepository;
    private final SizeService sizeService;
    private final ItemService itemService;

    public ItemInfoService(ItemInfoRepository itemInfoRepository, SizeService sizeService, ItemService itemService) {
        this.itemInfoRepository = itemInfoRepository;
        this.sizeService = sizeService;
        this.itemService = itemService;
    }

    @Transactional
    @SneakyThrows
    public Boolean addSizeToItemInfo(String itemInfoId, String sizeId) {
        ItemInfo itemInfo = itemInfoRepository.findById(itemInfoId)
                .orElseThrow(() -> new BadRequestException("Item info with this id does not exist"));
        Size size = sizeService.findSizeById(sizeId);
        itemInfo.getSizes().add(size);
        return itemInfoRepository.save(itemInfo).getSizes().stream().anyMatch(size1 -> size1.getId().equals(sizeId));
    }

    @Transactional
    public ItemInfoDTO save(ItemInfoInput itemInfoInput) {
        if (itemService.findById(itemInfoInput.getItemId()) == null) {
            throw new IllegalArgumentException("Item with this id does not exist");
        }
        return ItemInfoDTO.fromEntity(itemInfoRepository.save(itemInfoInput.toEntity()));
    }
}
