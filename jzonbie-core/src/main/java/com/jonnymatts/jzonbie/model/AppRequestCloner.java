package com.jonnymatts.jzonbie.model;

import com.jonnymatts.jzonbie.model.content.*;

import java.util.HashMap;
import java.util.Map;

import static com.jonnymatts.jzonbie.model.content.ArrayBodyContent.arrayBody;
import static com.jonnymatts.jzonbie.model.content.LiteralBodyContent.literalBody;
import static com.jonnymatts.jzonbie.model.content.ObjectBodyContent.objectBody;
import static com.jonnymatts.jzonbie.model.content.StringBodyContent.stringBody;

class AppRequestCloner {

    static AppRequest clone(AppRequest appRequest) {
        final AppRequest copy = new AppRequest();
        copy.setPath(appRequest.getPath());
        copy.setMethod(appRequest.getMethod());
        copy.setQueryParams(copyMap(appRequest.getQueryParams()));
        copy.setHeaders(copyMap(appRequest.getHeaders()));
        copy.setBody(copyBodyContent(appRequest.getBody()));
        return copy;
    }

    private static <K, V> HashMap<K, V> copyMap(Map<K, V> map) {
        return map == null ? null : new HashMap<>(map);
    }

    private static BodyContent copyBodyContent(BodyContent bodyContent) {
        if(bodyContent == null) return null;
        if(bodyContent instanceof LiteralBodyContent) return literalBody(((LiteralBodyContent)bodyContent).getContent());
        if(bodyContent instanceof ArrayBodyContent) return arrayBody(((ArrayBodyContent)bodyContent).getContent());
        if(bodyContent instanceof StringBodyContent) return stringBody(((StringBodyContent)bodyContent).getContent());
        return objectBody(new HashMap<>(((ObjectBodyContent)bodyContent).getContent()));
    }
}
