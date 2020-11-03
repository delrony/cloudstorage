package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credential-save")
    public String saveCredential(@ModelAttribute("newCredential")CredentialForm credentialForm, Authentication auth, RedirectAttributes attributes) {
        User user = this.userService.getUser(auth.getName());
        Integer userid = user.getUserid();
        credentialForm.setUserid(userid);

        this.credentialService.saveCredential(credentialForm);

        attributes.addFlashAttribute("fromCredential", "OK");
        attributes.addFlashAttribute("changeSuccess", true);

        return "redirect:/home";
    }

    @GetMapping("/credential-delete")
    public String deleteCredential(@RequestParam(name="credential_id") Integer credentialid, Authentication auth, RedirectAttributes attributes) {
        User user = userService.getUser(auth.getName());
        Credential credential = credentialService.getCredential(credentialid);

        if ((credential != null) && (user != null)
                && user.getUserid().intValue() == credential.getUserid().intValue()) {
            this.credentialService.deleteCredential(credentialid);
            attributes.addFlashAttribute("changeSuccess", true);
        } else {
            attributes.addFlashAttribute("changeError", true);
        }

        attributes.addFlashAttribute("fromCredential", "OK");

        return "redirect:/home";
    }
}
