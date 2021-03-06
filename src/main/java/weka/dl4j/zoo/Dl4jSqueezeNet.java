/*
 * WekaDeeplearning4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WekaDeeplearning4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WekaDeeplearning4j.  If not, see <https://www.gnu.org/licenses/>.
 *
 * ResNet50.java
 * Copyright (C) 2017-2018 University of Waikato, Hamilton, New Zealand
 */

package weka.dl4j.zoo;

import org.deeplearning4j.nn.conf.CacheMode;
import org.deeplearning4j.nn.graph.ComputationGraph;
import weka.dl4j.Preferences;
import weka.dl4j.PretrainedType;

/**
 * A WEKA version of DeepLearning4j's ResNet50 ZooModel.
 *
 * @author Steven Lang
 * @author Rhys Compton
 */
public class Dl4jSqueezeNet extends AbstractZooModel {

    private static final long serialVersionUID = -9163009640895595245L;

    public Dl4jSqueezeNet() {
        setPretrainedType(PretrainedType.IMAGENET);
        setNumFExtractOutputs(1000);
        setFeatureExtractionLayer("global_average_pooling2d_5");
        setOutputLayer("loss");
    }

//    @Override
//    public void setPretrainedType(PretrainedType pretrainedType) {
//        setPretrainedType(pretrainedType,
//                1000,
//                "global_average_pooling2d_5",
//                "loss");
//    }

    @Override
    public ComputationGraph init(int numLabels, long seed, int[] shape, boolean filterMode) {
        org.deeplearning4j.zoo.model.SqueezeNet net = org.deeplearning4j.zoo.model.SqueezeNet.builder()
                .cacheMode(CacheMode.NONE)
                .workspaceMode(Preferences.WORKSPACE_MODE)
                .inputShape(shape)
                .numClasses(numLabels)
                .build();

        ComputationGraph defaultNet = net.init();

        return attemptToLoadWeights(net, defaultNet, seed, numLabels, filterMode);
    }


    @Override
    public int[][] getShape() {
        return org.deeplearning4j.zoo.model.ResNet50.builder().build().metaData().getInputShape();
    }
}
