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

import java.nio.ByteBuffer;

class Shape {
    final int parentOffset;
    final int figureOffset;
    final OpenGisType openGisType;

    Shape(int parentOffset, int figureOffset, OpenGisType openGisType) {
        this.figureOffset = figureOffset;
        this.parentOffset = parentOffset;
        this.openGisType = openGisType;
    }

    static int getByteSize() {
        return 9;
    }

    void store(ByteBuffer buffer) {
        buffer.putInt(parentOffset);
        buffer.putInt(figureOffset);
        buffer.put(openGisType.byteValue);
    }
}
