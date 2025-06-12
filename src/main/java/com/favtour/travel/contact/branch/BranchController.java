package com.favtour.travel.contact.branch;

import com.favtour.travel.core.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Branch>>> getAllBranches() {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "All branches are ready", branchService.getAllBranches()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> getBranchById(@PathVariable int id) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Branch found", branchService.getBranchById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Branch>> createBranch(@RequestPart Branch branch,
                                                           @RequestPart MultipartFile coverPhoto) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Branch has been created successfully",
                        branchService.createBranch(branch, coverPhoto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> updateBranch(@PathVariable int id,
                                                            @RequestPart(required = false) Branch branch,
                                                            @RequestPart(required = false) MultipartFile coverPhoto) {

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Branch updated successfully",
                        branchService.updateBranch(id, branch, coverPhoto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBranch(@PathVariable int id) {

        branchService.deleteBranch(id);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Branch has been deleted successfully", null));
    }
}
