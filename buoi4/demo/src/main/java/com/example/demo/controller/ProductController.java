import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.model.Product;
import com.example.demo.model.Category;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("product") Product newProduct,
            BindingResult result,
            @RequestParam("category_id") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        productService.updateImage(newProduct, imageProduct);
        newProduct.setCategory(categoryService.get(categoryId));
        productService.add(newProduct);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product product = productService.get(id);
        if (product == null) {
            return "error/404";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        if (!imageProduct.isEmpty()) {
            productService.updateImage(product, imageProduct);
        }

        productService.update(product);
        return "redirect:/products";
    }
}
