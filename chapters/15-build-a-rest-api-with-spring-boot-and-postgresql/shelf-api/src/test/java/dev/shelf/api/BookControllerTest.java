package dev.shelf.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    BookService service;

    @Test
    void unknownBookIs404() throws Exception {
        when(service.find(99)).thenThrow(new BookNotFoundException(99));

        mvc.perform(get("/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("no book with id 99"));
    }

    @Test
    void blankTitleIs400() throws Exception {
        String json = """
                {"isbn": "9781400033416", "title": "", "author": "Toni Morrison", "year": 1987}
                """;

        mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").value("must not be blank"));
    }
}
