package dev.sangco.hm.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RandomTransferRequestDto {

    private Integer totalCount;
    private String totalAmount;
    private Long xUserId;
    private String xRoomId;
    private String xRandomToken;

    @Builder
    public RandomTransferRequestDto(Integer totalCount, String totalAmount, Long xUserId, String xRoomId, String xRandomToken) {
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
        this.xUserId = xUserId;
        this.xRoomId = xRoomId;
        this.xRandomToken = xRandomToken;
    }

    public void addHeaders(Long xUserId, String xRoomId) {
        this.xUserId = xUserId;
        this.xRoomId = xRoomId;
    }
}
