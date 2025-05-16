package org.example.springsecurityapp.web;

import jakarta.validation.Valid;
import org.example.springsecurityapp.entities.Product;
import org.example.springsecurityapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String index(Model model,
                        @RequestParam(defaultValue = "") String keyword, // Mot-clé pour la recherche par nom
                        @RequestParam(defaultValue = "0") int page,      // Page courante (par défaut = 0)
                        @RequestParam(defaultValue = "20") int size) {   // Taille de page (nombre d'éléments par page)

        // Recherche les produits dont le nom contient le mot-clé, avec pagination (page, taille)
        Page<Product> productPage = productRepository
                .findByNameContains(keyword, PageRequest.of(page, size));

        // Nombre total de pages disponibles
        int totalPages = productPage.getTotalPages();

        // Page actuelle
        int currentPage = page;

        // Page de début pour l'affichage des numéros de pagination (maximum 2 avant la page actuelle)
        int start = Math.max(0, currentPage - 2);

        // Page de fin pour l'affichage (maximum 2 après la page actuelle)
        int end = Math.min(totalPages - 1, currentPage + 2);

        // Ajouter la page de produits au modèle (utilisée dans le template HTML)
        model.addAttribute("productPage", productPage);

        // Ajouter la page actuelle (pour gérer l'état actif dans la pagination)
        model.addAttribute("currentPage", currentPage);

        // Ajouter le mot-clé pour garder le filtre lors de la navigation dans les pages
        model.addAttribute("keyword", keyword);

        // Début de la séquence des pages affichées
        model.addAttribute("startPage", start);

        // Fin de la séquence des pages affichées
        model.addAttribute("endPage", end);

        // Retourne le nom du template Thymeleaf à afficher (products.html)
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


    @GetMapping("/admin/edit")
    public String edit(@RequestParam Long id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "editproduct";
    }


    @PostMapping("/admin/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@Valid Product updatedProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editproduct";
        }

        productRepository.save(updatedProduct);
        return "redirect:/user/index";
    }


    @GetMapping("/user/search")
    @PreAuthorize("hasRole('USER')")
    public String search(@RequestParam String keyword, Model model) {
        List<Product> productList;
        if (keyword != null && !keyword.trim().isEmpty()) {
            productList = productRepository.findByNameContainingIgnoreCase(keyword);
        } else {
            productList = productRepository.findAll();
        }
        model.addAttribute("productList", productList);
        model.addAttribute("keyword", keyword);
        return "products";
    }


}
