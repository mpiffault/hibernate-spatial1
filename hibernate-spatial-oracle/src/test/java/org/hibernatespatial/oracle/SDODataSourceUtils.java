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

import org.hibernatespatial.test.DataSourceUtils;
import org.hibernatespatial.test.SQLExpressionTemplate;

import java.sql.SQLException;


/**
 * Created by IntelliJ IDEA.
 * User: maesenka
 * Date: Mar 26, 2010
 * Time: 4:39:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class SDODataSourceUtils extends DataSourceUtils {

    public SDODataSourceUtils(String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPass, SQLExpressionTemplate sqlExpressionTemplate) {
        super(jdbcDriver, jdbcUrl, jdbcUser, jdbcPass, sqlExpressionTemplate);
    }

    @Override
    public void afterCreateSchema() {
        super.afterCreateSchema();
        try {
            setGeomMetaDataTo2D();
            createIndex();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

//    public void setGeomMetaDataTo4D() throws SQLException {
//        String sql1 = "delete from user_sdo_geom_metadata where TABLE_NAME = 'GEOMTEST'";
//        String sql2 = "insert into user_sdo_geom_metadata values (" +
//                "  'GEOMTEST'," +
//                "  'geom'," +
//                "  SDO_DIM_ARRAY(" +
//                "    SDO_DIM_ELEMENT('X', -180, 180, 0.00001)," +
//                "    SDO_DIM_ELEMENT('Y', -90, 90, 0.00001)" +
//                "     ,SDO_DIM_ELEMENT('Z', -1000, 10000, 0.001)," +
//                "     SDO_DIM_ELEMENT('M', -10000, 100000, 0.001)" +
//                "    )," +
//                "  4326)";
//        executeStatement(sql1);
//        executeStatement(sql2);
//    }
//
//
//    public void dropIndex() throws SQLException {
//        String sql = "drop index idx_spatial_geomtest";
//        executeStatement(sql);
//    }
//

    private void createIndex() throws SQLException {
        String sql = "create index idx_spatial_geomtest on geomtest (geom) indextype is mdsys.spatial_index";
        executeStatement(sql);
    }

    private void setGeomMetaDataTo2D() throws SQLException {
        String sql1 = "delete from user_sdo_geom_metadata where TABLE_NAME = 'GEOMTEST'";
        String sql2 = "insert into user_sdo_geom_metadata values (" +
                "  'GEOMTEST'," +
                "  'geom'," +
                "  SDO_DIM_ARRAY(" +
                "    SDO_DIM_ELEMENT('X', -180, 180, 0.00001)," +
                "    SDO_DIM_ELEMENT('Y', -90, 90, 0.00001)" +
                "    )," +
                "  4326)";
        executeStatement(sql1);
        executeStatement(sql2);

    }


}
