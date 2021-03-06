/*
 * $Id:$
 *
 * This file is part of Hibernate Spatial, an extension to the
 * hibernate ORM solution for geographic data.
 *
 * Copyright © 2007-2010 Geovise BVBA
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
import com.vividsolutions.jts.geom.GeometryCollection;
import org.hibernatespatial.mgeom.MGeometryFactory;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractGeometryCollectionDecoder<T extends GeometryCollection> extends AbstractDecoder<T> {

    public AbstractGeometryCollectionDecoder(MGeometryFactory factory) {
        super(factory);
    }

    @Override
    protected OpenGisType getOpenGisType() {
        return OpenGisType.GEOMETRYCOLLECTION;
    }

    @Override
    protected T createNullGeometry() {
        return createGeometry((List<Geometry>) null, false);
    }

    @Override
    protected T createGeometry(SqlServerGeometry nativeGeom) {
        return createGeometry(nativeGeom, 0);
    }

    @Override
    protected T createGeometry(SqlServerGeometry nativeGeom, int shapeIndex) {
        int startChildIdx = shapeIndex + 1;
        List<Geometry> geometries = new ArrayList<Geometry>(nativeGeom.getNumShapes());
        for (int childIdx = startChildIdx; childIdx < nativeGeom.getNumShapes(); childIdx++) {
            if (!nativeGeom.isParentShapeOf(shapeIndex, childIdx)) continue;
            AbstractDecoder<?> decoder = (AbstractDecoder<?>) Decoders.decoderFor(nativeGeom.getOpenGisTypeOfShape(childIdx));
            Geometry geometry = decoder.createGeometry(nativeGeom, childIdx);
            geometries.add(geometry);
        }
        return createGeometry(geometries, nativeGeom.hasMValues());
    }

    abstract protected T createGeometry(List<Geometry> geometries, boolean hasM);


}
