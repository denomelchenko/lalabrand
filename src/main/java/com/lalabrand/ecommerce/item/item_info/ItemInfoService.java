package com.lalabrand.ecommerce.item.item_info;

import org.springframework.stereotype.Service;

@Service
public class ItemInfoService {
    private final ItemInfoRepository itemInfoRepository;

    public ItemInfoService(ItemInfoRepository itemInfoRepository) {
        this.itemInfoRepository = itemInfoRepository;
    }

    public ItemInfoDTO save(ItemInfoInput itemInfoInput) {
        return ItemInfoDTO.fromEntity(itemInfoRepository.save(itemInfoInput.toEntity()));
    }
}
