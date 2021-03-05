package tacos;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;   // <2>

    // 由于WebMvcTest要初始化所有Controller的Bean, 而controller的Bean要求注入Repository,
    // 因此需要mock3个Repository
    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private TacoRepository designRepository;

    @MockBean
    private OrderRepository orderRepository;
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))    // <3>
                .andExpect(status().isOk())  // <4>
                .andExpect(view().name("home"))  // <5>
                .andExpect(content().string(           // <6>
                containsString("Welcome To...")));
    }

}
