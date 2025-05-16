package com.webflux.boilerplate.service;

import com.webflux.boilerplate.repository.PersonRepository;
import com.webflux.boilerplate.service.impl.PersonServiceImpl;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * package com.webflux.boilerplate.service; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: PersonImplServiceTest.java, v 0.1 2025-05-14 2:22 PM John Brix Pomoy Exp $$
 */
public class PersonServiceImplTest {
    @InjectMocks
    private PersonService personService = new PersonServiceImpl();

    @Mock
    private PersonRepository personRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
