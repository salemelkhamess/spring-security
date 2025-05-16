package org.example.springsecurityapp.web;

import jakarta.validation.Valid;
import org.example.springsecurityapp.entities.Product;
import org.example.springsecurityapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;


    @GetMapping("/")

    public String home() {
        return "redirect:/user/index";
    }

    @GetMapping("/user/index")
    @PreAuthorize("hasRole('USER')")
    public String index (Model model) {

        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);

        return "products";
    }


    @GetMapping("/admin/addProduct")
    @PreAuthorize("hasRole('ADMIN')")

    public String addProduct( Model model) {
        model.addAttribute("product", new Product());
        return "addproduct";
    }


    @PostMapping("/admin/saveProduct")
    @PreAuthorize("hasRole('ADMIN')")

    public String saveProduct(@Valid  Product product , BindingResult bindingResult ,Model model) {
        if (bindingResult.hasErrors()) {
            return "addproduct";
        }
        productRepository.save(product);
        return "redirect:/user/index";
    }


    @PostMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")

    public String delete(@RequestParam  Long id) {
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }


    @GetMapping("/notAuthorized")
    public String notAuthenticated() {
        return "403";
    }
}
