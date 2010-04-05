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

package org.hibernatespatial.oracle;

import com.vividsolutions.jts.geom.Geometry;
import org.hibernatespatial.test.AbstractExpectationsFactory;
import org.hibernatespatial.test.NativeSQLStatement;

/**
 * Expectations factory for Oracle 10g (SDO_GEOMETRY).
 *
 * @Author Karel Maesen, Geovise BVBA
 */
public class SDOGeometryExpectationsFactory extends AbstractExpectationsFactory {

    private final SDOGeometryType decoder = new SDOGeometryType();

    public SDOGeometryExpectationsFactory() {
        super("hibernate-spatial-oracle10g-test.properties", new SDOGeometryExpressionTemplate());
    }

    @Override
    protected NativeSQLStatement createNativeTouchesStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Touch(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Touch(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) = 1 and t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeOverlapsStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Overlap(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Overlap(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) = 1 and t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeRelateStatement(Geometry geom, String matrix) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Relate(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326), '" + matrix + "') from GEOMTEST T where MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Relate(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326), '" + matrix + "') = 1 and t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeIntersectsStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Intersects(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Intersects(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) = 1 and t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeFilterStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, 1 from GEOMTEST t where SDO_FILTER(t.GEOM, MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326).GEOM)  = 'TRUE' ", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeDistanceStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Distance(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeDimensionSQL() {
        return createNativeSQLStatement("select ID, MDSYS.OGC_DIMENSION(MDSYS.ST_GEOMETRY.FROM_SDO_GEOM( T.GEOM)) FROM GEOMTEST T");
    }

    @Override
    protected NativeSQLStatement createNativeBufferStatement(Double distance) {
        return createNativeSQLStatement("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Buffer(?).GEOM from GEOMTEST T where t.GEOM.SDO_SRID = 4326", new Double[]{distance});
    }

    @Override
    protected NativeSQLStatement createNativeConvexHullStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Union(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)).ST_ConvexHull().GEOM from GEOMTEST T where t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeIntersectionStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Intersection(MDSYS.ST_GEOMETRY.FROM_WKT(?,4326)).GEOM FROM GEOMTEST t where t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeDifferenceStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Difference(MDSYS.ST_GEOMETRY.FROM_WKT(?,4326)).GEOM FROM GEOMTEST t where t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeSymDifferenceStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_SymmetricDifference(MDSYS.ST_GEOMETRY.FROM_WKT(?,4326)).GEOM FROM GEOMTEST t where t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeGeomUnionStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Union(MDSYS.ST_GEOMETRY.FROM_WKT(?,4326)).GEOM FROM GEOMTEST t where t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeAsTextStatement() {
        return createNativeSQLStatement("select t.ID, t.GEOM.GET_WKT() FROM GEOMTEST T");
    }

    @Override
    protected NativeSQLStatement createNativeSridStatement() {
        return createNativeSQLStatement("SELECT t.ID, t.GEOM.SDO_SRID FROM GEOMTEST t");
    }

    @Override
    protected NativeSQLStatement createNativeIsSimpleStatement() {
        return createNativeSQLStatement("SELECT t.ID, MDSYS.OGC_ISSIMPLE(MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM)) FROM GEOMTEST t where MDSYS.OGC_ISSIMPLE(MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM)) = 1");

    }

    @Override
    protected NativeSQLStatement createNativeIsEmptyStatement() {
        return createNativeSQLStatement("SELECT t.ID, MDSYS.OGC_ISEMPTY(MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM)) FROM GEOMTEST t");
    }

    @Override
    protected NativeSQLStatement createNativeBoundaryStatement() {
        return createNativeSQLStatement("SELECT t.ID, MDSYS.OGC_BOUNDARY(MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM)).GEOM FROM GEOMTEST t");
    }

    @Override
    protected NativeSQLStatement createNativeEnvelopeStatement() {
        return createNativeSQLStatement("SELECT t.ID, MDSYS.OGC_ENVELOPE(MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM)).GEOM FROM GEOMTEST t");
    }

    @Override
    protected NativeSQLStatement createNativeAsBinaryStatement() {
        return createNativeSQLStatement("select t.ID, t.GEOM.GET_WKB() FROM GEOMTEST T");
    }

    @Override
    protected NativeSQLStatement createNativeGeometryTypeStatement() {
        return createNativeSQLStatement("select t.id, CASE t.geom.Get_GType() WHEN 1 THEN 'POINT' WHEN 2 THEN 'LINESTRING' WHEN 3 THEN 'POLYGON' WHEN 5 THEN 'MULTIPOINT' WHEN 6 THEN 'MULTILINE' WHEN 7 THEN 'MULTIPOLYGON' END from GEOMTEST t");
    }

    @Override
    protected NativeSQLStatement createNativeWithinStatement(Geometry testPolygon) {
        return createNativeSQLStatementAllWKTParams("select t.id, mdsys.OGC_WITHIN( MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM), MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where mdsys.OGC_WITHIN( MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM), MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) = 1 and t.GEOM.SDO_SRID = 4326", testPolygon.toText());
    }

    @Override
    protected NativeSQLStatement createNativeEqualsStatement(Geometry testPolygon) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Equals(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Equals(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) = 1 and t.GEOM.SDO_SRID = 4326", testPolygon.toText());
    }

    @Override
    protected NativeSQLStatement createNativeCrossesStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Cross(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Cross(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) = 1 and t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeContainsStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Contains(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Contains(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) = 1 and t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected NativeSQLStatement createNativeDisjointStatement(Geometry geom) {
        return createNativeSQLStatementAllWKTParams("select t.id, MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Disjoint(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) from GEOMTEST T where MDSYS.ST_GEOMETRY.FROM_SDO_GEOM(t.GEOM).ST_Disjoint(MDSYS.ST_GEOMETRY.FROM_WKT(?, 4326)) = 1 and t.GEOM.SDO_SRID = 4326", geom.toText());
    }

    @Override
    protected Geometry decode(Object o) {
        return decoder.convert2JTS(o);
    }
}