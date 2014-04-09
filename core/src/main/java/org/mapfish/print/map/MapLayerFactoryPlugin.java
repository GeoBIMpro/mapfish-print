/*
 * Copyright (C) 2014  Camptocamp
 *
 * This file is part of MapFish Print
 *
 * MapFish Print is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MapFish Print is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MapFish Print.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mapfish.print.map;

import org.mapfish.print.attribute.map.MapLayer;
import org.mapfish.print.config.Template;

import java.util.Set;
import javax.annotation.Nonnull;

/**
 * Parses layer request data and creates a MapLayer from it.
 *
 * @param <Param> the type of object that will be populated from the JSON and passed to the factory to create the layer.
 * @author Jesse on 3/26/14.
 */
public interface MapLayerFactoryPlugin<Param> {

    /**
     * Return a set of all the values the json 'type' property should have for this plugin to apply (case insensitive).
     */
    Set<String> getTypeNames();

    /**
     * Create an instance of a param object.  Each instance must be new and unique. Instances must <em>NOT</em> be shared.
     * <p/>
     * The object will be populated from the json.  Each public field will be populated by looking up the value in the json.
     * <p/>
     * If a field in the Param object has the {@link org.mapfish.print.processor.HasDefaultValue} annotation then no exception
     * will be thrown if the json does not contain a value.
     * <p/>
     * <ul>
     * <li>String</li>
     * <li>Integer or int</li>
     * <li>Double or double</li>
     * <li>Float or float</li>
     * <li>Boolean or boolean</li>
     * <li>PJsonObject</li>
     * <li>URL</li>
     * <li>Any enum</li>
     * <li>PJsonArray</li>
     * <li>any type with a 0 argument constructor</li>
     * <li>array of any of the above (String[], boolean[], PJsonObject[], ...)</li>
     * </ul>
     * <p/>
     * If there is a public <code>{@value org.mapfish.print.attribute.map.MapLayerParamParser#POST_CONSTRUCT_METHOD_NAME}()</code>
     * method then it will be called after the fields are all set.
     * <p/>
     * In the case where the a parameter type is a normal POJO (not a special case like PJsonObject, URL, enum, double, etc...)
     * then it will be assumed that the json data is a json object and the parameters will be recursively parsed into the new
     * object as if it is also MapLayer parameter object.
     */
    Param createParameter();

    /**
     * Inspect the json data and return Optional&lt;MapLayer> or Optional.absent().
     *
     * @param template  the configuration related to the current request.
     * @param layerData an object populated from the json for the layer
     */
    @Nonnull
    MapLayer parse(@Nonnull Template template, @Nonnull Param layerData) throws Throwable;

}