/*
 * $Id:$
 *
 * This file is part of Hibernate Spatial, an extension to the
 * hibernate ORM solution for geographic data.
 *
 * Copyright © 2009 Geovise BVBA
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * For more information, visit: http://www.hibernatespatial.org/
 */

package org.hibernatespatial.sqlserver.convertors;

import com.vividsolutions.jts.geom.Geometry;
import org.hibernatespatial.mgeom.MGeometryFactory;

abstract class AbstractDecoder<G extends Geometry> implements Decoder<G> {

    //TODO -- get GeometryFactory from HSExtension
    private final MGeometryFactory geometryFactory = new MGeometryFactory();

    public G decode(SqlGeometryV1 nativeGeom) {
        if (!accepts(nativeGeom))
            throw new IllegalArgumentException(getClass().getSimpleName() + " received object of type " + nativeGeom.openGisType());
        if (nativeGeom.isEmpty())
            return createNullGeometry();
        G result = createGeometry(nativeGeom);
        setSrid(nativeGeom, result);
        return result;
    }

    public abstract boolean accepts(SqlGeometryV1 nativeGeom);

    protected abstract G createNullGeometry();

    protected abstract G createGeometry(SqlGeometryV1 nativeGeom);

    protected MGeometryFactory getGeometryFactory() {
        return this.geometryFactory;
    }

    protected void setSrid(SqlGeometryV1 sqlNative, G result) {
        if (sqlNative.getSrid() != null)
            result.setSRID(sqlNative.getSrid());
    }


}