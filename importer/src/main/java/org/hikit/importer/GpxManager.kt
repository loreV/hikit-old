package org.hikit.importer

import com.google.inject.Inject
import org.hikit.common.data.CoordinatesWithAltitude
import org.hikit.common.helper.GpxHelper
import org.hikit.importer.model.TrailPreparationModel
import org.hikit.importer.service.AltitudeServiceHelper
import java.nio.file.Path

class GpxManager @Inject constructor(val gpxHelper: GpxHelper,
                                     val altitudeService: AltitudeServiceHelper) {

    private val emptyDefaultString = ""

    fun getTrailPreparationFromGpx(tempFile: Path): TrailPreparationModel? {
        val gpx = gpxHelper.readFromFile(tempFile)
        val track = gpx.tracks.first()
        val segment = track.segments.first()

        val coordinatesWithAltitude = segment.points.map { point ->
            CoordinatesWithAltitude(point.longitude.toDegrees(), point.latitude.toDegrees(),
                    altitudeService.getAltitudeByLongLat(point.latitude.toDegrees(), point.longitude.toDegrees()))
        }

        return TrailPreparationModel(
                track.name.orElse(emptyDefaultString),
                track.description.orElse(emptyDefaultString),
                coordinatesWithAltitude.first(),
                coordinatesWithAltitude.last(),
                coordinatesWithAltitude
        )
    }

}