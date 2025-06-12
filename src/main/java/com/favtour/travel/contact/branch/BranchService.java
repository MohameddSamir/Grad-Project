package com.favtour.travel.contact.branch;

import com.favtour.travel.core.util.FileStorageService;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.shared.FileStorageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final FileStorageService fileStorageService;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch getBranchById(int id) {
        return branchRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Branch not found"));
    }

    @Transactional
    public Branch createBranch(Branch branch, MultipartFile coverPhoto) {

        validateFields(branch, coverPhoto);

        Branch savedBranch= branchRepository.save(branch);

        addCoverPhoto(savedBranch, coverPhoto);

        return savedBranch;
    }

    public Branch updateBranch(int id, Branch updatedBranch, MultipartFile coverPhoto) {

        Branch branch = getBranchById(id);

        if(updatedBranch != null) {
            updateFields(updatedBranch, branch);
        }

        if(coverPhoto != null && !coverPhoto.isEmpty()) {
            addCoverPhoto(branch, coverPhoto);
        }
        return branchRepository.save(branch);
    }

    public void deleteBranch(int id) {
        Branch branch = getBranchById(id);
        branchRepository.delete(branch);
    }

    private void updateFields(Branch updatedBranch, Branch branch) {
        if(updatedBranch.getName() != null) {
            branch.setName(updatedBranch.getName());
        }
        if(updatedBranch.getLocation() != null) {
            branch.setLocation(updatedBranch.getLocation());
        }
        if(updatedBranch.getEmail() != null) {
            branch.setEmail(updatedBranch.getEmail());
        }
        if(updatedBranch.getPhone() != null) {
            branch.setPhone(updatedBranch.getPhone());
        }
    }

    private void validateFields(Branch branch, MultipartFile coverPhoto) {
        if(branch.getName() == null){
            throw new IllegalArgumentException("Name is required");
        }
        if(branch.getLocation() == null){
            throw new IllegalArgumentException("Location is required");
        }
        if(branch.getEmail() == null){
            throw new IllegalArgumentException("Email is required");
        }
        if(branch.getPhone() == null){
            throw new IllegalArgumentException("Phone is required");
        }
        if(coverPhoto == null || coverPhoto.isEmpty()){
            throw new IllegalArgumentException("You must provide a cover photo");
        }
    }

    private void addCoverPhoto(Branch savedBranch, MultipartFile coverPhoto) {

        String uploadDirectory= "photos/branches";
        String coverPhotoName= System.currentTimeMillis() + "_" +coverPhoto.getOriginalFilename();

        savedBranch.setCoverPhoto(coverPhotoName);
        try{
            fileStorageService.saveImage(coverPhoto, uploadDirectory, coverPhotoName);
        }catch (IOException ioe){
            throw new FileStorageException("Could not save cover photo");
        }
    }
}
