
package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exceptions.AdNotFoundResponseException;
import ru.skypro.homework.exceptions.CurrentUserNotFoundResponseException;
import ru.skypro.homework.exceptions.PermissionDeniedDeleteResponseException;
import ru.skypro.homework.exceptions.PermissionDeniedUpdateResponseException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.models.AdModel;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdServiceImplTest {

    private AdServiceImpl adService;
    private AdRepository adRepository;
    private UserRepository userRepository;
    private AdMapper adMapper;
    private ImageServiceImpl imageService;
    private final UserModel adminUser = new UserModel(1L,"admin@example.com","Oleg","Olegov",
            "+7 (777) 777-77-77", Role.ADMIN,"","");
    private final UserModel easyUser = new UserModel(1L,"user@example.com","Anna","Olegov",
            "+7 (777) 777-77-77", Role.USER,"","");

    @TempDir
    Path tempDir;

    @BeforeEach
    void setup() {
        adRepository = mock(AdRepository.class);
        userRepository = mock(UserRepository.class);
        adMapper = mock(AdMapper.class);
        imageService = mock(ImageServiceImpl.class);

        adService = new AdServiceImpl(adRepository, userRepository, adMapper, imageService);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user@example.com", null));
    }

    @Test
    void getAdsCount_shouldReturnAllAds() {
        AdModel model = new AdModel();
        Ad ad = new Ad();
        when(adRepository.findAll()).thenReturn(List.of(model));
        when(adMapper.toDto(model)).thenReturn(ad);

        Ads result = adService.getAdsCount();
        assertEquals(1, result.getCount());
        assertEquals(1, result.getResults().size());
    }

    @Test
    void getUserAdsCount_shouldReturnUserAds() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setEmail("user@example.com");
        AdModel model = new AdModel();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(adRepository.findByAuthorId(1L)).thenReturn(List.of(model));
        when(adMapper.toDto(model)).thenReturn(new Ad());

        Ads ads = adService.getUserAdsCount();
        assertEquals(1, ads.getCount());
    }

    @Test
    void getAdById_shouldReturnAd() {
        AdModel model = new AdModel();
        model.setId(1L);

        when(adRepository.findById(1L)).thenReturn(Optional.of(model));
        when(imageService.imageValidator(any())).thenReturn(true);
        when(adMapper.toExtendedDto(model)).thenReturn(new ExtendedAd());

        assertNotNull(adService.getAdById(1L));
        verify(adRepository).findById(1L);
    }

    @Test
    void getAdById_shouldThrow_whenNotFound() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AdNotFoundResponseException.class, () -> adService.getAdById(1L));
    }

    @Test
    void createAd_shouldReturnCreatedAd() {
        CreateOrUpdateAd dto = new CreateOrUpdateAd("Title", 100, "desc");
        UserModel user = new UserModel();
        user.setEmail("user@example.com");
        AdModel model = new AdModel();
        model.setId(1L);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(adMapper.fromCreateDto(dto)).thenReturn(model);
        when(adRepository.save(any())).thenReturn(model);
        when(adRepository.findById(any())).thenReturn(Optional.of(model));

        when(adMapper.toDto(any())).thenReturn(new Ad());

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[0]);
        assertNotNull(adService.createAd(dto, image));
    }

    @Test
    void updateAd_shouldReturnUpdatedAd_NotCurrentUser() {
        AdModel model = new AdModel();
        CreateOrUpdateAd dto = new CreateOrUpdateAd("Title", 100, "desc");

        when(adRepository.findById(1L)).thenReturn(Optional.of(model));
        when(adRepository.save(any())).thenReturn(model);
        when(adMapper.toDto(model)).thenReturn(new Ad());

        assertThrows(CurrentUserNotFoundResponseException.class, () -> {
            adService.updateAd(1L, dto);
        });
    }

    @Test
    void updateAd_shouldReturnUpdatedAd_Valid() {
        AdModel model = new AdModel();
        CreateOrUpdateAd dto = new CreateOrUpdateAd("Title", 100, "desc");

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(adminUser));
        when(adRepository.findById(4L)).thenReturn(Optional.of(model));
        when(adRepository.save(any())).thenReturn(model);
        when(adMapper.toDto(model)).thenReturn(new Ad());

        Ad updated = adService.updateAd(4L, dto);
        assertNotNull(updated);
    }


    @Test
    void updateAdImage_shouldUpdateImage_Failed() {
        AdModel model = new AdModel();
        model.setId(1L);

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(easyUser));
        when(adRepository.findById(1L)).thenReturn(Optional.of(model));
        when(adRepository.save(model)).thenReturn(model);

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[]{1});
        assertThrows((PermissionDeniedUpdateResponseException.class), () -> adService.updateAdImage(1L, image));
    }

    @Test
    void deleteAd_shouldRemoveAd_NoPermission() {
        AdModel model = new AdModel();
        model.setId(1L);

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(easyUser));
        when(adRepository.findById(1L)).thenReturn(Optional.of(model));
        doNothing().when(adRepository).deleteById(1L);
        assertThrows(PermissionDeniedDeleteResponseException.class, () -> {
            adService.deleteAd(1L);
        });
    }

    @Test
    void deleteAd_shouldRemoveAd_Valid() {
        AdModel model = new AdModel();
        model.setId(1L);

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(adminUser));
        when(adRepository.findById(1L)).thenReturn(Optional.of(model));
        doNothing().when(adRepository).deleteById(1L);
        adService.deleteAd(1L);
        verify(adRepository, times(1)).deleteById(1L);
    }


    @Test
    void getCurrentUser_shouldThrow_whenUserMissing() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(CurrentUserNotFoundResponseException.class, () -> adService.getUserAdsCount());
    }
}
