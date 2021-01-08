package dev.sangco.hm.web;

import dev.sangco.hm.domain.RandomTransfer;
import dev.sangco.hm.domain.RandomTransferReceiver;
import dev.sangco.hm.service.RandomTransferService;
import dev.sangco.hm.web.dto.RandomTransferRequestDto;
import dev.sangco.hm.web.dto.RandomTransferResponseDto;
import dev.sangco.hm.web.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        RandomTransfer randomTransfer = randomTransferService.findOne(randomTransferId)
                .orElseThrow(IllegalStateException::new);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-RANDOM-TOKEN", randomTransfer.getToken());
        return ResponseEntity.ok().headers(httpHeaders).build();
    }

    @PostMapping("/apply")
    public ResponseEntity applyRandomTransfer(@RequestHeader(name = "X-USER-ID") Long xUserId,
                                              @RequestHeader(name = "X-ROOM-ID") String xRoomId,
                                              @RequestHeader(name = "X-RANDOM-TOKEN") String xRandomToken) throws Exception {
        RandomTransferRequestDto requestDto = RandomTransferRequestDto.builder()
                .xUserId(xUserId)
                .xRoomId(xRoomId)
                .xRandomToken(xRandomToken)
                .build();
        RandomTransferReceiver randomTransferReceiver = randomTransferService.transferOne(requestDto);
        return ResponseEntity.ok().body(randomTransferReceiver.getAmount());
    }

    @GetMapping
    public ResponseEntity<Result<RandomTransferResponseDto>> getRandomTransfer(@RequestHeader(name = "X-USER-ID") Long xUserId,
                                                                               @RequestHeader(name = "X-ROOM-ID") String xRoomId,
                                                                               @RequestHeader(name = "X-RANDOM-TOKEN") String xRandomToken) throws Exception {
        RandomTransferRequestDto requestDto = RandomTransferRequestDto.builder()
                .xUserId(xUserId)
                .xRoomId(xRoomId)
                .xRandomToken(xRandomToken)
                .build();
        RandomTransfer randomTransfer = randomTransferService.findOne(requestDto);
        return ResponseEntity.ok().body(new Result<>(new RandomTransferResponseDto(randomTransfer)));
    }

}
