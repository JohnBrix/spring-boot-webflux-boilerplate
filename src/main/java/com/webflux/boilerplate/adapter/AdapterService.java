package com.webflux.boilerplate.adapter;

import com.webflux.boilerplate.model.HttpPersonResponse;
import io.reactivex.rxjava3.core.Single;

/**
 * package com.webflux.boilerplate.adapter; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: AdapterService.java, v 0.1 2025-05-09 9:42 PM John Brix Pomoy Exp $$
 */
public interface AdapterService {

    Single<HttpPersonResponse> callSomethingAPI(String request);
}
