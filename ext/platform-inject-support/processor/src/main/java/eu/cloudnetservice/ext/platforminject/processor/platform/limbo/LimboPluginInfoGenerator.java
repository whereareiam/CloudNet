/*
 * Copyright 2019-2024 CloudNetService team & contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.cloudnetservice.ext.platforminject.processor.platform.limbo;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.yaml.YamlFormat;
import eu.cloudnetservice.ext.platforminject.api.data.ParsedPluginData;
import eu.cloudnetservice.ext.platforminject.processor.id.CharRange;
import eu.cloudnetservice.ext.platforminject.processor.id.PluginIdGenerator;
import eu.cloudnetservice.ext.platforminject.processor.infogen.NightConfigInfoGenerator;
import eu.cloudnetservice.ext.platforminject.processor.util.ConfigUtil;
import lombok.NonNull;

@SuppressWarnings("DuplicatedCode") // nukkit...
final class LimboPluginInfoGenerator extends NightConfigInfoGenerator {

  // ^[A-Za-z0-9 _.-]+$
  // while bukkit does allow spaces in the plugin name it would replace the spaces with underscores at runtime anyway
  private static final PluginIdGenerator PLUGIN_NAME_GENERATOR = PluginIdGenerator.withInfiniteLength()
    .registerRange(0, '_', CharRange.range(' '))
    .registerRange(
      0,
      '_',
      CharRange.range('_'),
      CharRange.range('.'),
      CharRange.range('-'),
      CharRange.range('A', 'Z'),
      CharRange.range('a', 'z'),
      CharRange.range('0', '9'));

  public LimboPluginInfoGenerator() {
    super(YamlFormat.defaultInstance(), "plugin.limbo.yml");
  }

  @Override
  protected void applyPlatformInfo(
    @NonNull Config target,
    @NonNull ParsedPluginData pluginData,
    @NonNull String platformMainClassName
  ) {
    target.add("version", pluginData.version());
    target.add("main", platformMainClassName);
    target.add("name", PLUGIN_NAME_GENERATOR.convert(pluginData.name()));

    // optional values
    ConfigUtil.putIfPresent(target, "description", pluginData.description());
    ConfigUtil.putIfValuesPresent(target, "author", pluginData.authors());
  }
}
