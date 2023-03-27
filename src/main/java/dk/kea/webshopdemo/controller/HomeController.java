package dk.kea.webshopdemo.controller;

import dk.kea.webshopdemo.model.Product;
import dk.kea.webshopdemo.repository.ProductRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController
	{

		ProductRepository productRepository;
		public HomeController(ProductRepository productRepository){
			this.productRepository=productRepository;
		}
		@GetMapping("/")
		public String index(Model model){
			model.addAttribute("products",productRepository.getAll());
			return "index";
		}
		//fra <a tagget:
		@GetMapping("/create")
		public String showCreate(){
			// vis create siden
			return "create";
		}
		// fra form-action:
		@PostMapping("/create")
		public String createProduct(@RequestParam("product-name") String new_name,
											 @RequestParam("product-price") double new_price)
			{
			// lav et nyt product
			Product newProduct=new Product();
			newProduct.setName(new_name);
			newProduct.setPrice(new_price);
			// gem nyt produkt i repository
			productRepository.addProduct(newProduct);

			//retur til product-siden
			return "redirect:/";
		}
		//vis update side for product ud fra parameter i URL
		@GetMapping("/update/{id}")
		public String showUpdate(@PathVariable("id") int updateId, Model model)
		{ // find produkt med id=updateId
			Product updateProduct=productRepository.findProductByID(updateId);

			//tilføj produkt til viewmodel så det kan bruges i Thymeleaf
			model.addAttribute("product",updateProduct);

			// fortæl Spring hvilken side der skal vises
			return "update";
		}

		@PostMapping("/update")
		public String updateProduct(@RequestParam("product-name") String updateName,
											 @RequestParam("product-price") double updatePrice, @RequestParam("product-id") int updateId)
			{ //lav produkt ud fra parametre
				Product updateProduct=new Product(updateId,updateName,updatePrice);
				//kald opdater i repository
				productRepository.updateProduct(updateProduct);

			return "redirect:/";
		}
		@GetMapping("/delete/{id}")
		public String deleteProduct(@PathVariable("id") int id){
			// slet fra product
			productRepository.deleteById(id);
			return  "redirect:/";
		}
	}
