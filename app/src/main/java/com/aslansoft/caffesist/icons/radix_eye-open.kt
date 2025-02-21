import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val EyeOpen: ImageVector
	get() {
		if (_EyeOpen != null) {
			return _EyeOpen!!
		}
		_EyeOpen = ImageVector.Builder(
            name = "EyeOpen",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.EvenOdd
			) {
				moveTo(7.5f, 11f)
				curveTo(4.8029f, 11f, 2.5295f, 9.6218f, 1.0962f, 7.5f)
				curveTo(2.5295f, 5.3782f, 4.8029f, 4f, 7.5f, 4f)
				curveTo(10.1971f, 4f, 12.4705f, 5.3782f, 13.9038f, 7.5f)
				curveTo(12.4705f, 9.6218f, 10.1971f, 11f, 7.5f, 11f)
				close()
				moveTo(7.5f, 3f)
				curveTo(4.3079f, 3f, 1.6564f, 4.7064f, 0.076f, 7.235f)
				curveTo(-0.0253f, 7.3971f, -0.0253f, 7.6029f, 0.076f, 7.765f)
				curveTo(1.6564f, 10.2936f, 4.3079f, 12f, 7.5f, 12f)
				curveTo(10.6921f, 12f, 13.3436f, 10.2936f, 14.924f, 7.765f)
				curveTo(15.0253f, 7.6029f, 15.0253f, 7.3971f, 14.924f, 7.235f)
				curveTo(13.3436f, 4.7064f, 10.6921f, 3f, 7.5f, 3f)
				close()
				moveTo(7.5f, 9.5f)
				curveTo(8.6046f, 9.5f, 9.5f, 8.6046f, 9.5f, 7.5f)
				curveTo(9.5f, 6.3954f, 8.6046f, 5.5f, 7.5f, 5.5f)
				curveTo(6.3954f, 5.5f, 5.5f, 6.3954f, 5.5f, 7.5f)
				curveTo(5.5f, 8.6046f, 6.3954f, 9.5f, 7.5f, 9.5f)
				close()
			}
		}.build()
		return _EyeOpen!!
	}

private var _EyeOpen: ImageVector? = null
