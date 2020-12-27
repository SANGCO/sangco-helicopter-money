package dev.sangco.hm.web;

import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.service.RandomTransferService;
import dev.sangco.hm.web.dto.RandomTransferRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/random/transfers")
@RequiredArgsConstructor
public class RandomTransferController {

    private final RandomTransferService randomTransferService;

    @PostMapping
    public ResponseEntity createRandomTransfer(@RequestHeader(name = "X-USER-ID") Long xUserId,
                                               @RequestHeader(name = "X-ROOM-ID") String xRoomId,
                                               @RequestBody RandomTransferRequestDto requestDto) {
        requestDto.addHeaders(xUserId, xRoomId);
        Long randomTransferId = randomTransferService.saveRandomTransfer(requestDto);
        RandomTransfer randomTransfer = randomTransferService.findOne(randomTransferId);
        return new ResponseEntity<>(OK);
    }

}
