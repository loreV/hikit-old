package org.hikit.common.data;

import com.google.gson.JsonElement;

import java.util.List;

public class Poi {

    public static final String COLLECTION_NAME = "core.Poi";

    public static final String POSITION = "position";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String TAGS = "tags";
    public static final String OTHER_NAMES = "otherNames";
    public static final String RESOURCES_LINKS = "resourcesLinks";
    public static final String TYPES = "types";
    public static final String TRAIL_REF = "trails";
    public static final String POST_CODE = "postCode";

    private Position position;
    private String name;
    private String description;
    private List<String> tags;
    private List<String> otherNames;
    private JsonElement geo;
    private List<String> resourcesLinks;
    private List<String> types;
    private List<TrailReference> trailReferences;
    private String postCode;

    public Poi(Position position,
               String name,
               List<String> tags,
               List<String> otherNames,
               String description,
               JsonElement geo,
               List<String> resourcesLinks,
               List<String> types,
               List<TrailReference> trailReferences,
               String postCode) {
        this.position = position;
        this.name = name;
        this.tags = tags;
        this.otherNames = otherNames;
        this.description = description;
        this.geo = geo;
        this.resourcesLinks = resourcesLinks;
        this.types = types;
        this.trailReferences = trailReferences;
        this.postCode = postCode;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getOtherNames() {
        return otherNames;
    }

    public JsonElement getGeo() {
        return geo;
    }

    public List<String> getResourcesLinks() {
        return resourcesLinks;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<TrailReference> getTrailReferences() {
        return trailReferences;
    }

    public String getPostCode() {
        return postCode;
    }

    public static final class PoiBuilder {
        private Position position;
        private String name;
        private List<String> tags;
        private List<String> otherNames;
        private String description;
        private JsonElement geo;
        private List<String> resourcesLinks;
        private List<String> types;
        private List<TrailReference> trailCodes;
        private String postCode;

        private PoiBuilder() {
        }

        public static PoiBuilder aPoi() {
            return new PoiBuilder();
        }

        public PoiBuilder withPosition(Position position) {
            this.position = position;
            return this;
        }

        public PoiBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PoiBuilder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public PoiBuilder withOtherNames(List<String> otherNames) {
            this.otherNames = otherNames;
            return this;
        }

        public PoiBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PoiBuilder withGeo(JsonElement geo) {
            this.geo = geo;
            return this;
        }

        public PoiBuilder withResourcesLinks(List<String> resourcesLinks) {
            this.resourcesLinks = resourcesLinks;
            return this;
        }

        public PoiBuilder withTypes(List<String> types) {
            this.types = types;
            return this;
        }

        public PoiBuilder withTrailRef(List<TrailReference> trailCodes) {
            this.trailCodes = trailCodes;
            return this;
        }

        public PoiBuilder withPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Poi build() {
            return new Poi(position, name, tags, otherNames, description, geo, resourcesLinks, types, trailCodes, postCode);
        }
    }
}
