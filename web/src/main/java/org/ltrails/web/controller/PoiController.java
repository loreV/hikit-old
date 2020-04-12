package org.ltrails.web.controller;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.ltrails.common.JsonUtil;
import org.ltrails.common.data.Poi;
import org.ltrails.common.data.helper.GsonBeanHelper;
import org.ltrails.common.response.PoiRestResponse;
import org.ltrails.common.response.Status;
import org.ltrails.web.PoiManager;
import org.ltrails.web.request.PoiGeoRequest;
import org.ltrails.web.request.validator.PoiGeoRequestValidator;
import org.ltrails.web.request.validator.PoiRequestValidator;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.ltrails.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.ltrails.web.configuration.ConfigurationManager.ACCEPT_TYPE;


public class PoiController implements PublicController {

    private final Logger LOG = getLogger(PoiController.class.getName());

    private static final String PREFIX = API_PREFIX + "/trails";

    public static final String COMMA_ARRAY_ELEM_SEP = ",";
    public static String PARAM_TRAIL_CODE = "trailCode";
    public static String PARAM_POST_CODE = "postCode";
    public static String PARAM_COUNTRY = "country";
    public static String PARAM_TYPES = "types";

    private final PoiManager poiManager;
    private final PoiRequestValidator poiRequestValidator;
    private final PoiGeoRequestValidator poiGeoRequestValidator;
    private final GsonBeanHelper gsonBeanHelper;

    @Inject
    public PoiController(final PoiManager poiManager,
                         final PoiRequestValidator poiRequestValidator,
                         final PoiGeoRequestValidator poiGeoRequestValidator,
                         final GsonBeanHelper gsonBeanHelper) {
        this.poiManager = poiManager;
        this.poiRequestValidator = poiRequestValidator;
        this.poiGeoRequestValidator = poiGeoRequestValidator;
        this.gsonBeanHelper = gsonBeanHelper;
    }

    public PoiRestResponse get(final Request request, final Response response) {
        final List<String> errorMessages = poiRequestValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return buildErrorResponse(errorMessages);
        }
        final String code = request.queryMap().get(PARAM_TRAIL_CODE).value();
        final String postCodes = request.queryMap().get(PARAM_POST_CODE).value();
        final String types = request.queryMap().get(PARAM_TYPES).value();
        final String country = request.queryMap().get(PARAM_COUNTRY).value();

        final List<Poi> trailsByTrailCode = poiManager.getByTrailPostCodeCountryType(
                isBlank(code) ? EMPTY : code,
                !isEmpty(postCodes) ? asList(postCodes.split(COMMA_ARRAY_ELEM_SEP)) :
                        Collections.emptyList(),
                isBlank(country) ? EMPTY : country,
                !isEmpty(types) ? asList(types.split(COMMA_ARRAY_ELEM_SEP)) :
                        Collections.emptyList()
        );
        return buildPoiResponse(trailsByTrailCode);
    }

    public PoiRestResponse getGeo(final Request request, final Response response) {
        final List<String> errorMessages = poiGeoRequestValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return buildErrorResponse(errorMessages);
        }
        final PoiGeoRequest poiGeoRequest = Objects.requireNonNull(gsonBeanHelper.getGsonBuilder())
                .fromJson(request.body(), PoiGeoRequest.class);
        final List<Poi> trailsNearby = poiManager.getByGeo(poiGeoRequest.getCoords(),
                poiGeoRequest.getDistance().intValue(),
                poiGeoRequest.getUom(),
                poiGeoRequest.getTypes()
        );
        return buildPoiResponse(trailsNearby);
    }

    @NotNull
    private PoiRestResponse buildPoiResponse(final List<Poi> pois) {
        final PoiRestResponse.PoiRestResponseBuilder poiRestResponseBuilder = PoiRestResponse.
                PoiRestResponseBuilder.aPoiRestResponse().withTrails(pois);
        if (pois.isEmpty()) {
            return poiRestResponseBuilder
                    .withMessages(Collections.singletonList("No POI found"))
                    .withStatus(Status.ERROR).build();
        }
        return poiRestResponseBuilder
                .withMessages(Collections.emptyList())
                .withStatus(Status.OK).build();
    }

    @NotNull
    private PoiRestResponse buildErrorResponse(final List<String> errorMessages) {
        return PoiRestResponse.PoiRestResponseBuilder.aPoiRestResponse()
                .withTrails(Collections.emptyList())
                .withMessages(errorMessages)
                .withStatus(Status.ERROR).build();
    }

    public void init() {
        Spark.get(format("%s", PREFIX), ACCEPT_TYPE, this::get, JsonUtil.json());
        Spark.post(format("%s/geo", PREFIX), ACCEPT_TYPE, this::getGeo, JsonUtil.json());
        LOG.info("POI CONTROLLER Started");
    }


}
