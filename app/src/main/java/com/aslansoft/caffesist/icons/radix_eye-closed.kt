import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val EyeClosed: ImageVector
	get() {
		if (_EyeClosed != null) {
			return _EyeClosed!!
		}
		_EyeClosed = ImageVector.Builder(
            name = "EyeClosed",
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
				moveTo(14.7649f, 6.07596f)
				curveTo(14.9991f, 6.2223f, 15.0703f, 6.5308f, 14.9239f, 6.7649f)
				curveTo(14.4849f, 7.4674f, 13.9632f, 8.1065f, 13.3702f, 8.6631f)
				lineTo(14.5712f, 9.86406f)
				curveTo(14.7664f, 10.0593f, 14.7664f, 10.3759f, 14.5712f, 10.5712f)
				curveTo(14.3759f, 10.7664f, 14.0593f, 10.7664f, 13.8641f, 10.5712f)
				lineTo(12.6011f, 9.30817f)
				curveTo(11.805f, 9.9028f, 10.9089f, 10.3621f, 9.9337f, 10.651f)
				lineTo(10.383f, 12.3277f)
				curveTo(10.4544f, 12.5944f, 10.2961f, 12.8685f, 10.0294f, 12.94f)
				curveTo(9.7627f, 13.0115f, 9.4885f, 12.8532f, 9.417f, 12.5865f)
				lineTo(8.95917f, 10.8775f)
				curveTo(8.4874f, 10.958f, 8.0004f, 10.9999f, 7.5f, 10.9999f)
				curveTo(6.9996f, 10.9999f, 6.5126f, 10.958f, 6.0408f, 10.8775f)
				lineTo(5.58299f, 12.5864f)
				curveTo(5.5115f, 12.8532f, 5.2374f, 13.0115f, 4.9706f, 12.94f)
				curveTo(4.7039f, 12.8686f, 4.5456f, 12.5944f, 4.6171f, 12.3277f)
				lineTo(5.06625f, 10.651f)
				curveTo(4.0911f, 10.3621f, 3.195f, 9.9028f, 2.3989f, 9.3081f)
				lineTo(1.1359f, 10.5712f)
				curveTo(0.9406f, 10.7664f, 0.6241f, 10.7664f, 0.4288f, 10.5712f)
				curveTo(0.2335f, 10.3759f, 0.2335f, 10.0593f, 0.4288f, 9.8641f)
				lineTo(1.62982f, 8.66303f)
				curveTo(1.0368f, 8.1064f, 0.5151f, 7.4674f, 0.0761f, 6.7649f)
				curveTo(-0.0703f, 6.5308f, 0.0009f, 6.2223f, 0.2351f, 6.076f)
				curveTo(0.4692f, 5.9296f, 0.7777f, 6.0008f, 0.9241f, 6.235f)
				curveTo(1.4035f, 7.0021f, 1.989f, 7.6806f, 2.6623f, 8.2427f)
				curveTo(2.6732f, 8.251f, 2.6837f, 8.2597f, 2.694f, 8.269f)
				curveTo(4.009f, 9.3553f, 5.6554f, 9.9999f, 7.5f, 9.9999f)
				curveTo(10.3078f, 9.9999f, 12.6564f, 8.5063f, 14.076f, 6.235f)
				curveTo(14.2223f, 6.0008f, 14.5308f, 5.9296f, 14.7649f, 6.076f)
				close()
			}
		}.build()
		return _EyeClosed!!
	}

private var _EyeClosed: ImageVector? = null
