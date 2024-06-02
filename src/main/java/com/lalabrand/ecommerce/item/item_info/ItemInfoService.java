package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.ItemService;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.item.size.SizeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    public ItemInfoDTO addSizeToItemInfo(String itemInfoId, String sizeId) {
        ItemInfo itemInfo = itemInfoRepository.findById(itemInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Item info with this id does not exist"));
        if (itemInfo.getSizes().stream().anyMatch(size -> size.getId().equals(sizeId))) {
            throw new IllegalArgumentException("Size with id: " + sizeId + " already exists in this item info");
        }
        Size size = sizeService.findSizeById(sizeId);
        itemInfo.getSizes().add(size);
        System.out.println(itemInfoRepository.save(itemInfo).getSizes().toString());
        ItemInfoDTO itemInfoDTO = ItemInfoDTO.fromEntity(itemInfoRepository.save(itemInfo));
        System.out.println(itemInfoDTO.getSizes());
        return itemInfoDTO;
    }

    @Transactional
    public ItemInfoDTO save(ItemInfoInput itemInfoInput) {
        itemService.findById(itemInfoInput.getItemId());
        return ItemInfoDTO.fromEntity(itemInfoRepository.save(itemInfoInput.toEntity()));
    }

    @Transactional
    public ItemInfoDTO addSizesToItemInfo(String itemInfoId, Set<String> sizeIds) {
        if (sizeIds.size() == 1) {
            return addSizeToItemInfo(itemInfoId, sizeIds.iterator().next());
        }
        ItemInfo itemInfo = itemInfoRepository.findById(itemInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Item info with id: " + itemInfoId + " does not exist"));
        sizeIds.forEach(sizeId -> {
            if (itemInfo.getSizes().stream().anyMatch(size -> size.getId().equals(sizeId))) {
                throw new IllegalArgumentException("Size with id: " + sizeId + " already exists in this item info");
            }
            Size size = sizeService.findSizeById(sizeId);
            itemInfo.getSizes().add(size);
        });
        return ItemInfoDTO.fromEntity(itemInfoRepository.save(itemInfo));
    }
}
